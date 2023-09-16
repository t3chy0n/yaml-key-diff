# yaml-key-diff

# YAML Key Diff Command Line Tool Documentation

This utility provides several commands to compare YAML files or directories.

## Usage
To use the `yaml-key-diff` tool, use the following syntax:

```console
yaml-key-diff <command> <source> <destination>
```

Where:

- `<command>` - The operation you want the tool to perform.
- `<source>` - The initial directory or file for comparison.
- `<destination>` - The directory or file you want to compare with the source.

## Commands
The `yaml-key-diff` tool provides the following commands:

1. **dirDiff:** Compares the YAML files in two directories. This command takes as arguments the source and destination directories.

   Usage:

   ```console
   yaml-key-diff dirDiff <source_dir> <destination_dir>
   ```

2. **fileDiff:** Compares two YAML files. This command takes as arguments two file paths, the source and destination.

   Usage:

   ```console
   yaml-key-diff fileDiff <source_file> <destination_file>
   ```

3. **missingFiles:** Finds YAML files present in the source directory but missing in the destination directory.

   Usage:

   ```console
   yaml-key-diff missingFiles <source_dir> <destination_dir>
   ```

4. **--help:** Prints the help message, showing all possible commands and usages.

   Usage:

   ```console
   yaml-key-diff --help
   ```

Please replace `<source_dir>`, `<destination_dir>`, `<source_file>`, `<destination_file>` with your actual directories or file paths. Remember to quote directories or file paths that contain spaces.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

## Known issues
* Refactor is highly recommended :D