# SqlDelight Issue 1422 Repro
Sample project that reproduces https://github.com/square/sqldelight/issues/1422.

It compares the behavior between
[SqlDelight 0.9.0](https://github.com/square/sqldelight/releases/tag/0.9.0) + [SqlBrite 3.2.0](https://github.com/square/sqlbrite)
vs. [SqlDelight 1](https://github.com/square/sqldelight) with respect to no-op transactions and their effects on the emissions of
any subscribed RxJava Query Observables.

To compare the results, simply run
```
./gradlew test
```
or
```bash
./gradlew :sqldelight-prerelease-and-sqlbrite:test
./gradlew :sqldeligh-1:test
```

The tests from both modules should have the same results, however `sqldelight-prerelease-and-sqlbrite` passes while
`sqldeligh-1` fails.
