# The Emergence Of Hierarchy In Transportation Networks

## Bibliographic Information

- Row ID: `paper-2005-09`
- Year: 2005
- Authors: Bhanu M. Yerra and David M. Levinson
- Venue: Annals of Regional Science 39(3):541-553 (2005)
- DOI: 10.1007/s00168-005-0230-4
- Citation: Yerra, B. M., and Levinson, D. M. (2005). The emergence of hierarchy in transportation networks. Annals of Regional Science, 39(3), 541-553. https://doi.org/10.1007/s00168-005-0230-4

## Package Status

This package is intended for `READY-TO-UPLOAD/PUBLIC` as a source-code and sample-input archive. The paper is simulation/model based and does not use human-subjects or restricted empirical microdata. The staged assets are the best available local model source corresponding to the paper.

Before making a public GitHub repository, add an explicit source-code license. No privacy restriction was found in the staged assets.

## Package Contents

- `paper/Emergence.pdf`: local reference copy for audit convenience.
- `code/ndapplication_source_original/`: Java source for the NDApplication research application.
- `code/demo_source_original/`: Java source for the demo/applet-style version.
- `data/sample_network_inputs/`: sample network/topology inputs copied from the Demo `Java Classes` folder.
- `documentation/Documentation_NDApp_original.doc`: original 2003 Word documentation for NDApplication.
- `documentation/Documentation_NDApp_extracted.txt`: text extraction of the Word documentation.
- `documentation/PAPER_FIRST_VALIDATION.md`: paper-first validation note.
- `documentation/SOURCE_BOUNDARY.md`: inclusion and exclusion rationale.
- `metadata/SOURCE_MANIFEST.csv`: file-level package manifest with checksums.
- `metadata/SAMPLE_INPUT_MANIFEST.csv`: sample input summary.
- `metadata/EXCLUDED_LOCAL_FILES.csv`: excluded local source categories and reasons.

## Evidence Match

The paper describes a network dynamics process using exogenous network and land-use inputs, Dijkstra least-cost paths, gravity OD estimation, traffic assignment, link revenue, maintenance/cost, investment, and iterative speed updates. The staged Java source implements that same sequence. In particular, `NetworkDynamics.java` calls `DijkstrasAlgo`, `TAssignment`, `Revenue`, and `Investment2`, and the program documentation describes project files, network and land-use files, coefficients, running the dynamics, and browsing speed/volume outputs.

The source was inspected but not compiled in this pass because this Mac currently has no local Java runtime installed.

## Excluded Material

The broader local paper folder contains drafts, reviewer replies, letters, copyright paperwork, presentations, poster files, copied figures, and reference images. Those are not data/code archive assets and were excluded. Compiled `.class` files were also excluded because the `.java` source is included.

<!-- package-hardening-status:start -->
## Package Hardening Status

Generated: 2026-05-20 13:10:44 AEST

- Pipeline: `READY-TO-UPLOAD/PUBLIC`
- Sidecars added/updated: `PACKAGE_STATUS.md`, `PACKAGE_MANIFEST.csv`, `LICENSE_STATUS.md`.
- Paper reference copies are for local audit convenience and are not public-upload assets without rights review.
- Final GitHub upload should use the manifest include statuses and the license-status note.
<!-- package-hardening-status:end -->
