#!/bin/bash

# Проверяем, что передано два аргумента
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <app-name> <service-port> <telegram-bot-token>"
    exit 1
fi

# Присваиваем аргументы переменным
APP_NAME=$1
CONTAINER_NAME=$APP_NAME-container
SERVICE_PORT=$2
TELEGRAM_BOT_TOKEN=$3

docker run \
		-e TELEGRAM.BOT.TOKEN=$TELEGRAM_BOT_TOKEN \
		--workdir=/app \
		-p 9033:$SERVICE_PORT \
		--name $CONTAINER_NAME \
		 $APP_NAME