# Source Boundary

## Included

The package includes the Java source for the NDApplication and Demo programs, sample network text inputs from the Demo `Java Classes` folder, and the original/extracted NDApplication documentation.

This boundary is intended to preserve the model source and minimal sample inputs needed to understand and revive the paper's simulation code, without turning the archive into a dump of every project-era artifact.

## Excluded

The broader Annals of Regional Science paper folder contains drafts, submitted manuscripts, reviewer replies, letters, copyright paperwork, presentations, poster files, copied figures, and reference images. These were excluded because they are not paper/data/code reproducibility assets.

Compiled `.class` files were excluded because the corresponding Java source files are included. Broad experiment output folders were also excluded because they appear to be derived outputs rather than the minimal source package; they can be regenerated if the legacy Java code is revived.

The thesis files in `/Users/dlev2617/Documents/Students/BhanuYerra` were not copied into this package. They may be useful contextual documentation, but the package is limited to the paper reference copy, source code, sample inputs, and program documentation.

## Compile Verification

On 2026-05-22, the preserved source files were copied to scratch directories and compile-tested with Homebrew OpenJDK 17.0.19. The original package files were not edited. The scratch compile required only mechanical legacy-source normalization: line-ending conversion, renaming `Graph.java` to `DirectedGraph.java` where the file declares `public class DirectedGraph`, and removing the invalid default-package `import NetworkDynamics;` line. See `JAVA_COMPILE_VERIFICATION.md`.
