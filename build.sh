#!/bin/bash

APP_NAME="socialnetwork"

APP_VERSION="0.0.1-SNAPSHOT"

# Путь к директории проекта
PROJECT_DIR="$(pwd)"

# Путь к конечному JAR-файлу
TARGET_DIR="$PROJECT_DIR/target"
JAR_FILE="$TARGET_DIR/${APP_NAME}-${APP_VERSION}.jar"

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}Начинаем сборку Spring Boot приложения...${NC}"

if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Ошибка: Maven не установлен. Пожалуйста, установите Maven.${NC}"
    exit 1
fi

echo "Очистка и сборка проекта..."
mvn clean package -DskipTests || {
    echo -e "${RED}Ошибка при сборке проекта${NC}"
    exit 1
}

if [ ! -f "$JAR_FILE" ]; then
    echo -e "${RED}Ошибка: JAR-файл не найден в $JAR_FILE${NC}"
    exit 1
fi

echo -e "${GREEN}Сборка успешно завершена!${NC}"
echo "JAR-файл находится по пути: $JAR_FILE"

read -p "Хотите запустить приложение сейчас? (y/n): " answer
if [ "$answer" = "y" ] || [ "$answer" = "Y" ]; then
    echo "Запускаем приложение..."
    java -jar "$JAR_FILE"
else
    echo "Приложение готово к запуску командой: java -jar $JAR_FILE"
fi