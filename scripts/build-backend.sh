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
docker build -t "$APP_NAME"-image .
