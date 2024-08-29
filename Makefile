# metadata below
PROJECT_PATH_PREFIX := ./..
BACKEND_PATH_PREFIX := $(PROJECT_PATH_PREFIX)/mater-rest
FRONTEND_PATH_PREFIX := $(PROJECT_PATH_PREFIX)/mater-webapp
CI_CD_PATH_PREFIX := $(PROJECT_PATH_PREFIX)/mater-ci-cd

# scripts location and names below
REMOVING_CONTAINER_SCRIPT := $(CI_CD_PATH_PREFIX)/scripts/remove-container.sh
REMOVING_IMAGE_SCRIPT := $(CI_CD_PATH_PREFIX)/scripts/remove-image.sh
ENVIRONMENT_VALIDATION_SCRIPT := $(CI_CD_PATH_PREFIX)/scripts/validate-env.py
BUILDING_BACKEND_SCRIPT := $(BACKEND_PATH_PREFIX)/scripts/build-backend.sh
BUILDING_FRONTEND_SCRIPT := $(FRONTEND_PATH_PREFIX)/scripts/build-frontend.sh
RUNNING_BACKEND_SCRIPT := $(BACKEND_PATH_PREFIX)/scripts/run-backend.sh
RUNNING_FRONTEND_SCRIPT := $(FRONTEND_PATH_PREFIX)/scripts/run-frontend.sh
COPYING_ENV_FILE_TO_BACKEND_SCRIPT := $(CI_CD_PATH_PREFIX)/scripts/copy-env-file-to-backend.sh
GETTING_ENV_VALUE_SCRIPT := $(CI_CD_PATH_PREFIX)/scripts/get-env-value.py

ENV_FILE_PATH := $(CI_CD_PATH_PREFIX)/.env
ENV_DENIED_VALUE := DEFAULT

DEFAULT_DOCKER_COMPOSE_FILE := docker-compose.yml
DOCKER_COMPOSE_PREFIX_PLUGIN = docker compose -f $(CI_CD_PATH_PREFIX)/$(DEFAULT_DOCKER_COMPOSE_FILE) --env-file $(ENV_FILE_PATH)
BACKEND_POM_XML := $(BACKEND_PATH_PREFIX)/pom.xml

# environment variables below
# todo: refactor in future on simple name and dependencies a web and rest
APP_NAME := $(shell mvn -f $(BACKEND_POM_XML) help:evaluate -Dexpression=project.name -q -DforceStdout)
APP_VERSION := $(shell mvn -f $(BACKEND_POM_XML) help:evaluate -Dexpression=project.version -q -DforceStdout)
VIRTUAL_CLOUD_DOMAIN := $(shell python3 $(GETTING_ENV_VALUE_SCRIPT) --key VIRTUAL_PRIVATE_CLOUD_DOMAIN)
TELEGRAM_BOT_TOKEN := $(shell python3 $(GETTING_ENV_VALUE_SCRIPT) --key TELEGRAM_BOT_TOKEN)

P12_FILE_PATH := $(BACKEND_PATH_PREFIX)/src/main/resources/copied.$(VIRTUAL_CLOUD_DOMAIN).p12

# other vars below
START_TIME := $(shell date +%s) # in seconds

# -------------------------------------------------------------------------------------------------

log-vars:
	@echo "App name «$(APP_NAME)»."
	@echo "App version «$(APP_VERSION)»."
	@echo "Domain «$(VIRTUAL_CLOUD_DOMAIN)»."
	@echo "Telegram bot token «$(TELEGRAM_BOT_TOKEN)»."
lv: log-vars # short alias

remove-backend-container:
	chmod +x $(REMOVING_CONTAINER_SCRIPT)
	$(REMOVING_CONTAINER_SCRIPT) mater-rest-container
rbc: remove-backend-container # short alias

remove-frontend-container:
	chmod +x $(REMOVING_CONTAINER_SCRIPT)
	$(REMOVING_CONTAINER_SCRIPT) mater-web-container
rfc: remove-frontend-container # short alias

remove-backend-image:
	chmod +x $(REMOVING_IMAGE_SCRIPT)
	$(REMOVING_IMAGE_SCRIPT) $(APP_NAME)
rbi: remove-backend-image # short alias

remove-frontend-image:
	chmod +x $(REMOVING_IMAGE_SCRIPT)
	$(REMOVING_IMAGE_SCRIPT) mater-web
rfi: remove-frontend-image # short alias

validate-env:
	python3 $(ENVIRONMENT_VALIDATION_SCRIPT) -e $(ENV_FILE_PATH) -d $(ENV_DENIED_VALUE) -s $(P12_FILE_PATH)
ve: validate-env # short alias

copy-environment-file-to-backend:
	chmod +x $(COPYING_ENV_FILE_TO_BACKEND_SCRIPT)
	$(COPYING_ENV_FILE_TO_BACKEND_SCRIPT)
ceftb: copy-environment-file-to-backend # short alias

build-backend: \
	remove-backend-image \
	copy-environment-file-to-backend \
	validate-env \
	process-maven
	chmod +x $(BUILDING_BACKEND_SCRIPT)
	$(BUILDING_BACKEND_SCRIPT) $(APP_NAME) $(APP_VERSION)
bb: build-backend # short alias

build-frontend: \
	remove-frontend-image \
	validate-env
	# process-npm
	chmod +x $(BUILDING_FRONTEND_SCRIPT)
	$(BUILDING_FRONTEND_SCRIPT)
bf: build-frontend # short alias

build-all: \
	build-backend \
	build-frontend
ba: build-all # short alias

run-backend:
	chmod +x $(RUNNING_BACKEND_SCRIPT)
	$(RUNNING_BACKEND_SCRIPT) $(APP_NAME) $(SERVICE_PORT) $(TELEGRAM_BOT_TOKEN)
rb: run-backend # short alias

run-frontend:
	chmod +x $(RUNNING_FRONTEND_SCRIPT)
	$(RUNNING_FRONTEND_SCRIPT) mater
rf: run-frontend # short alias

prepare-continuous-delivery:
	@echo "Continuous Delivery started..."

process-maven:
	mvn -f $(BACKEND_POM_XML) clean install

process-npm:
	npm -f $(FRONTEND_PATH_PREFIX) run build 

# in seconds
calculate-duration:
	@START_TIME=$(START_TIME); \
	END_TIME=$$(date +%s); \
	TIME_DIFF=$$((END_TIME - START_TIME)); \
	echo "Process duration is $$TIME_DIFF seconds."

start-continuous-delivery-brute-force: \
	prepare-continuous-delivery \
	remove-backend-container \
	remove-frontend-container \
	remove-backend-image \
	remove-frontend-image \
	validate-env \
	build-backend \
	build-frontend \
	run-backend \
	run-frontend \
	calculate-duration
scd-br: start-continuous-delivery-brute-force # short alias

composing-up:
	$(DOCKER_COMPOSE_PREFIX_PLUGIN) up
cu: composing-up # short alias

composing-down:
	$(DOCKER_COMPOSE_PREFIX_PLUGIN) down
cd: composing-down # short alias

start-continuous-delivery: \
	prepare-continuous-delivery \
	composing-down \
	remove-backend-image \
	remove-frontend-image \
	remove-frontend-container \
	remove-backend-container \
	validate-env \
	build-backend \
	build-frontend \
	composing-up \
	calculate-duration
scd: start-continuous-delivery # short alias
