![Banner](src/images/banner.png)

LibreDog is a web browser written in Java, utilizing the JavaFX platform. This browser is based on WebKit, that is a open source web browser engine that supports HTML5, JavaScript, CSS, DOM rendering and SVG graphics.

## Building

Tested with Amazon Corretto 18 and JavaFX SDK 18.

1. Clone this repository with `git clone` (or Download ZIP and extract to your desired folder).

2. Open the project folder in Visual Studio Code or VSCodium

3. Go to `.vscode/launch.json` and add vmArgs under configurations

```
"vmArgs": "--module-path /path/to/lib --add-modules=javafx.controls,javafx.fxml --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED"
```


On Linux:

![VMArgs-Linux](src/images/vmargs-linux.png)

On Windows:

![VMArgs-Linux](src/images/vmargs-linux.png)

4. Build the workspace and enjoy!
