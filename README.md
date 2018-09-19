# Deliverables
User can be able to see a list of my deliveries, including receivers photo and item description. User also can be able to see the delivery location on the map and full description when click on the delivery item.

# Source code
git clone https://github.com/celluloid90/Deliverables.git

# Build
cd /path/to/Deliverables

export ANDROID_HOME = /path/to/android/sdk

./gradlew build

# Run Unit test
./gradlew app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=shahin.android.exam.activity.SplashActivityTest
