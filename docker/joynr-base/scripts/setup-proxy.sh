#!/bin/bash

# Workaround for Fedora 30 since 'dnf' commands fail without it.
# If this gets removed again, please move [main] section header
# back into next modification of 'dnf.conf' below
cat > /etc/dnf/dnf.conf <<EOF
[main]
zchunk=false
EOF

if [ -z "$PROXY_HOST" ]
then
    echo "No proxy configured, using direct internet access."
    exit 0
fi
if [ -z "$PROXY_PORT" ]
then
    echo "PROXY_PORT not set."
    exit 1
fi
echo "Starting to setup proxy configuration, PROXY_HOST=$PROXY_HOST, PROXY_PORT=$PROXY_PORT."
echo "Setting up proxy configuration in /etc/dnf/dnf.conf"
# [main] section header already set by workaround above
cat >> /etc/dnf/dnf.conf <<EOF
gpgcheck=1
installonly_limit=3
clean_requirements_on_remove=false
proxy=http://$PROXY_HOST:$PROXY_PORT
sslverify=false
EOF
echo "Final Configuration /etc/dnf/dnf.conf:"
cat /etc/dnf/dnf.conf
echo "Setting up proxy configuration in /etc/wgetrc"
cat > /etc/wgetrc <<EOF
use_proxy=on
http_proxy=http://$PROXY_HOST:$PROXY_PORT/
https_proxy=http://$PROXY_HOST:$PROXY_PORT/
ftp_proxy=http://$PROXY_HOST:$PROXY_PORT/
check_certificate=off
EOF
echo "Final Configuration /etc/wgetrc:"
cat /etc/wgetrc
echo "Setting up insecure curl configuration in /etc/.curlrc"
cat > /etc/.curlrc << EOF
insecure
EOF
echo "Setting up proxy configuration in /etc/profile.d/use-my-proxy.sh"
cat > /etc/profile.d/use-my-proxy.sh <<EOF
echo "use-my-proxy.sh started"
PROXY_HOST=$PROXY_HOST
PROXY_PORT=$PROXY_PORT
http_proxy="http://$PROXY_HOST:$PROXY_PORT/"
https_proxy="http://$PROXY_HOST:$PROXY_PORT/"
ftp_proxy="http://$PROXY_HOST:$PROXY_PORT/"
HTTP_PROXY="http://$PROXY_HOST:$PROXY_PORT/"
HTTPS_PROXY="http://$PROXY_HOST:$PROXY_PORT/"
FTP_PROXY=http://$PROXY_HOST:$PROXY_PORT/
export PROXY_HOST
export PROXY_PORT
export http_proxy
export https_proxy
export ftp_proxy
export HTTP_PROXY
export HTTPS_PROXY
export FTP_PROXY
CURL_HOME=/etc
export CURL_HOME
echo "Setting up npm configuration in \$HOME/.npmrc"
cat > \$HOME/.npmrc << EOF2
strict-ssl=false
registry=http://registry.npmjs.org/
EOF2
MAVEN_OPTS="-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Xms2048m -Xmx2048m"
export MAVEN_OPTS
echo "use-my-proxy.sh finished"
EOF
echo "Proxy configuration finished."
exit 0
