#!/bin/bash

set -ex

base_image=joynr-complete-with-joynr
intermediate_container=joynr-installed-with-consumer-app-tmp
output_image=joynr-installed-with-consumer-app
current_work_dir=$(cd $(dirname $0) && pwd)
joynr_root=$(git rev-parse --show-toplevel)

docker create --name ${intermediate_container} \
    --privileged \
    -v ${joynr_root}:/data/src \
    -v ${current_work_dir}:/data/src-helper \
    -t ${base_image}

docker cp ${current_work_dir}/start-me-up.sh ${intermediate_container}:/home/joynr/start-me-up.sh

docker start ${intermediate_container}
docker exec -t --privileged ${intermediate_container} /data/src-helper/cpp-consumer.sh
docker stop ${intermediate_container}
docker commit ${intermediate_container} ${output_image}
docker rm -f ${intermediate_container}
