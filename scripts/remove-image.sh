#!/bin/bash

# Проверка, что имя образа передано в качестве аргумента
if [ -z "$1" ]; then
    echo "Usage: $0 <image_name>"
    exit 1
fi

# Имя образа
image_name="$1"-image

# Проверка существования образа
if [ "$(docker images -q $image_name)" ]; then
    # Удаление образа
    docker rmi "$image_name" -f
    echo "Image '$image_name' removed."
else
    echo "Image '$image_name' does not exist."
fi
