# scripts location and names below
REMOVING_IMAGE_SCRIPT := ./scripts/remove-image.sh
BUILDING_BACKEND_SCRIPT := ./scripts/build-backend.sh

BACKEND_POM_XML := ./pom.xml

# environment variables below
APP_NAME := $(shell mvn -f $(BACKEND_POM_XML) help:evaluate -Dexpression=project.name -q -DforceStdout)
APP_VERSION := $(shell mvn -f $(BACKEND_POM_XML) help:evaluate -Dexpression=project.version -q -DforceStdout)

# -------------------------------------------------------------------------------------------------

log-vars:
	@echo "App name «$(APP_NAME)»."
	@echo "App version «$(APP_VERSION)»."
lv: log-vars # short alias

process-maven:
	mvn -f $(BACKEND_POM_XML) clean install

remove-backend-image:
	chmod +x $(REMOVING_IMAGE_SCRIPT)
	$(REMOVING_IMAGE_SCRIPT) $(APP_NAME)
rbi: remove-backend-image # short alias

build-backend: \
	remove-backend-image \
	process-maven
	chmod +x $(BUILDING_BACKEND_SCRIPT)
	$(BUILDING_BACKEND_SCRIPT) $(APP_NAME) $(APP_VERSION)
bb: build-backend # short alias