# from docker-compose
ARG ICELL_JAVA_JRE_BASE_IMAGE

FROM ${ICELL_JAVA_JRE_BASE_IMAGE}

ENV JAVA_OPTS="-XshowSettings:vm -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Dquarkus.http.host=0.0.0.0"
ENV JAVA_DEBUG=true
ENV JAVA_DEBUG_PORT=*:5005

ARG SERVICE

ENV SERVICE=${SERVICE}

# orders from https://github.com/cescoffier/getting-started-quarkus-kafka/blob/master/src/main/docker/Dockerfile.fast-jar
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/quarkus-app/lib $HOME/deployments/lib/
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/quarkus-app/*.jar $HOME/deployments/
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/quarkus-app/app $HOME/deployments/app/
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/quarkus-app/quarkus $HOME/deployments/quarkus/
#COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP qs-services/$SERVICE/target/quarkus-app/*.txt $HOME/deployments/

EXPOSE 8080
EXPOSE 5005

CMD exec java $JAVA_OPTS -jar $HOME/deployments/quarkus-run.jar 
