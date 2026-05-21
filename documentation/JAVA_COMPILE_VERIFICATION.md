# Java Compile Verification

Verification date: 2026-05-22

Java runtime used:

```text
openjdk version "17.0.19" 2026-04-21
OpenJDK Runtime Environment Homebrew (build 17.0.19+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.19+0, mixed mode, sharing)
```

## Source Trees Checked

- `code/ndapplication_source_original/`
- `code/demo_source_original/`

The preserved source files in this package were not edited for the compile check. Instead, the `.java` files were copied to scratch directories under `/tmp`.

## Scratch Normalization

Modern `javac` can compile the legacy source after mechanical normalization:

- Convert old line endings to LF.
- Rename `Graph.java` to `DirectedGraph.java` where the file declares `public class DirectedGraph`.
- Remove the invalid default-package line `import NetworkDynamics;`.

These changes were applied only to scratch copies for verification. The archival package keeps the original source files as recovered.

## Commands

```bash
/opt/homebrew/opt/openjdk@17/bin/javac -Xlint:deprecation \
  -d /tmp/paper-2005-09-java-verify/nd-build \
  /tmp/paper-2005-09-java-verify/nd-src/*.java

/opt/homebrew/opt/openjdk@17/bin/javac -Xlint:deprecation \
  -d /tmp/paper-2005-09-java-verify/demo-build \
  /tmp/paper-2005-09-java-verify/demo-src/*.java
```

## Results

- NDApplication source compiled successfully and produced 23 `.class` files.
- Demo source compiled successfully and produced 17 `.class` files.
- The compiler reported legacy Java warnings only, mainly deprecated Applet/AWT methods and `Float(String)` constructor use.
- GUI launch, applet execution, and reproduction of published figures were not attempted.

## Interpretation

The package is a valid legacy Java source archive for the network dynamics model. Public upload does not require a private Java runtime; it only needs documentation of the normalization required by modern compilers and an explicit source-code license.
