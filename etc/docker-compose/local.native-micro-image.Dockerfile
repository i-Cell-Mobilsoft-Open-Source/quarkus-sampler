FROM quay.io/quarkus/quarkus-micro-image:2.0
ARG SERVICE
ENV SERVICE=${SERVICE}

WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --chown=1001:root qs-services/$SERVICE/target/$SERVICE-0.1.0-SNAPSHOT /work/application

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0"]
