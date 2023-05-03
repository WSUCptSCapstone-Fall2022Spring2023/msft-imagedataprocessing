# AGICamEndpoint

## Component summary
API web server which allows for different applications to easily interact with information from our system.

## Installation

### Prerequisites

- Java
- Gradle
- git
- Open port 4567

### Installation Steps

1. git clone `https://github.com/WSUCptSCapstone-Fall2022Spring2023/msft-imagedataprocessing.git`
2. traverse to AGICamEndpoint directory
3. ./gradlew jar

## Functionality

- Allows access/writing to the following information
  - NDVI data for a sensor within a range of dates.
  - Images captured by a sensor at a specific time
  - Updating processing plots for specific sensors
  - Updating capture schedule for specific sensors
  - Viewing of configuration for a specific sensor
- Creates a webserver on the localhost which other services could use to developr ontop of.

## Known Problems

- None.

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D
