# docker-compose adja
ARG ICELL_JAVA_JRE_BASE_IMAGE

FROM ${ICELL_JAVA_JRE_BASE_IMAGE}

ENV QUARKUS_LAUNCH_DEVMODE=true \
    JAVA_ENABLE_DEBUG=true

ENV JAVA_OPTS="-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Dquarkus.http.host=0.0.0.0"

ARG SERVICE

ENV SERVICE=${SERVICE}

COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/$SERVICE*-SNAPSHOT.jar $HOME/$SERVICE.jar

EXPOSE 8080
EXPOSE 5005

CMD exec java $JAVA_OPTS -jar $HOME/$SERVICE.jar
