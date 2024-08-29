#!/bin/bash

# Проверяем, что передано два аргумента
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <app-name> <app-version>"
    exit 1
fi

# Присваиваем аргументы переменным
APP_NAME=$1
APP_VERSION=$2

# Выводим информацию о сборке
echo "Building docker image $APP_NAME (version is $APP_VERSION)"

# Выполняем команду сборки Docker
docker build \
    --build-arg "APP_NAME=$APP_NAME" \
    --build-arg "APP_VERSION=$APP_VERSION" \
    -t "$APP_NAME" \
    -f ./../mater-rest/Dockerfile ./../mater-rest
