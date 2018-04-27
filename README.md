🚧  This is an experimental work in progress of an Elasticsearch client for Eclipse Vert.x

👋  Contributions / participation are welcome!

# Developer guide

## Tests

Tests are split in unit tests on classes named `*Test*` and integration tests on classes named `*IT*`.
Integration tests expect having an elasticsearch instance running locally.

You can run the following command to start a container locally:

```sh
docker-compose up
```

When running the tests from Maven, a Docker install will be automatically launched during the integration test phase.

* To skip unit tests, use `-DskipUnitTests` property.
* To skip integration tests, use `-DskipIntegTests` property.
* To skip all tests, use `-DskipTests` property.

## IntelliJ IDEA

To import the project in IntelliJ, make sure you disable

* `Enable annotation processing` in "Build Execution > Compiler > Annotation processors"
* `ArgLine` option in ""

