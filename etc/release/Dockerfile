# docker-compose adja
ARG ICELL_JAVA_17_JRE_BASE_IMAGE
ARG ARTIFACT_DOWNLOADER_IMAGE

################################################################################
# Default image customization
################################################################################
FROM ${ICELL_JAVA_17_JRE_BASE_IMAGE} AS base

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

################################################################################
# Download .jar
################################################################################
FROM ${ARTIFACT_DOWNLOADER_IMAGE} AS download

ARG POM_GROUP_ID
ARG POM_ARTIFACT_ID
ARG POM_VERSION
ARG POM_EXTENSION

ENV NEXUS_OBJECT_GROUP_ID=$POM_GROUP_ID
ENV NEXUS_OBJECT_ARTIFACT_ID=$POM_ARTIFACT_ID
ENV NEXUS_OBJECT_VERSION=$POM_VERSION
ENV NEXUS_OBJECT_EXTENSION=$POM_EXTENSION

ENV NEXUS_DOWNLOAD_OUTPUT_FILE_NAME=$NEXUS_OBJECT_ARTIFACT_ID.$NEXUS_OBJECT_EXTENSION

RUN ${HOME}/script/maven-search-download.sh
################################################################################
# Create production image
################################################################################
FROM base AS prod

ARG POM_ARTIFACT_ID
ARG POM_VERSION
ARG POM_EXTENSION

ENV JAR_FILE=$POM_ARTIFACT_ID-$POM_VERSION.$POM_EXTENSION
ENV APP_DIR='quarkus-app'
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

LABEL moduleName="$POM_ARTIFACT_ID"
LABEL moduleVersion="$POM_VERSION"

RUN mkdir ${APP_DIR}

COPY --from=download --chown=$SYSTEM_USER $HOME/download/$JAR_FILE $HOME/$APP_DIR/$JAR_FILE

EXPOSE 8080

CMD exec java $JAVA_OPTS -jar $HOME/$APP_DIR/$JAR_FILE
