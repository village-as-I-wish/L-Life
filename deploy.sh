#!/bin/bash

DOCKER_APP_NAME="l_life"


# backend 컨테이너 down
echo "Backend down"
docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml down

# 이전 이미지 삭제
docker rmi l_life:0.1

echo "Backend up"
docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml up --build -d

sleep 5

echo "Server On"

XIST_AFTER=$(docker-compose -p ${DOCKER_APP_NAME} -f docker-compose.yml ps | grep Up)
echo EXIST_AFTER
