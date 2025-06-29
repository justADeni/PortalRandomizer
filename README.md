## 🤔 What does it do?

* Want to disable Nether travel while still allowing players their individual Nether experiences without a central warp?

Portal's Uncertainty Principle disrupts traditional Nether travel by:

* **Randomized Portal Locations:** When a portal is created, its destination in the Nether is no longer fixed. All 16x16 Overworld chunk areas are randomly linked to all 16x16 chunk areas in Nether
* **Localized Randomness:** Within those 16x16 chunks, portal locations are also randomized, but relatively close, preventing block-to-block changes in portal location from creating millions-of-blocks differences
* **Portal Breaking on Failure:** If a suitable portal destination cannot be found within the constraints (due to obstructions or limitations), the attempted portal will simply break. 💥

## ⚙️ Configuration & Limitations

* **Coordinate Limits:** This plugin currently operates within the X and Z axis range of -8,388,608 to 8,388,607.  Portals outside this range will not function as intended.📏
* **No Config File (Currently):** As of this version, there are no configurable options. The randomization is fixed as described above. Future versions *may* include configuration options. 📝
* **Vanilla worlds:** Currently only supporting the two default worlds, world and world_nether 🗺️

## ✅ Installation & Usage

1. **Requirements:** Paper server running Java 21 or newer.  **Recommended: Java 24 for optimal performance.**
2. **Download:** Download the latest version of the plugin from [the latest Release](https://github.com/justADeni/PortalsUncertaintyPrinciple/releases/tag/0.4.0). ⬇️
3. **Installation:** Place the `.jar` file into your `plugins` folder on your Paper server.
4. **Restart:** Restart or reload your server to activate the plugin.

## 🐛 Bug Reporting & Support

If you encounter any bugs or have suggestions, please open an issue on [Github Issues](https://github.com/justADeni/PortalsUncertaintyPrinciple/issues).  Please include:

* A detailed description of the bug.
* Server logs (if applicable).
* Your server version and Java version. 🐞

## 🔧 For Developers

### Import using Maven

* Replace `Tag` with the newest version

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
    <groupId>com.github.justADeni</groupId>
    <artifactId>PortalsUncertaintyPrinciple</artifactId>
    <version>Tag</version>
</dependency>
```

### Import using Gradle
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.justADeni:PortalsUncertaintyPrinciple:Tag'
}
```

## 🚀 Plugin Events

* Keep in mind they're asynchronous.
* Both can be cancelled.
* Both have following getters: `getOriginLocation()`, `getDestinationLocation()`, `getPlayer()`.

```java
/**
 * Called when Player attempts to use Nether portal.
 * <p>
 * This event is {@code asynchronous} and should be used with caution.
 * Do not interact with Bukkit API methods that are not thread-safe unless switching to the main thread.
 */
public class NetherPortalUseEvent extends Event implements Cancellable {
```

```java
/**
 * Called when Player attempts to use Nether portal that doesn't
 * have a portal on the other end _and_ it's possible to be created.
 * <p>
 * This event is {@code asynchronous} and should be used with caution.
 * Do not interact with Bukkit API methods that are not thread-safe unless switching to the main thread.
 */
public class NetherPortalPairCreateEvent extends Event implements Cancellable {
```

---

**Disclaimer:** This plugin alters fundamental game mechanics. Use at your own risk!  Back up your world before installing any new plugins. 🙏