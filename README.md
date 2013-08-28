# balloons-game

### Deploying native bundle with icons for Windows

Open `build.xml` and find the path `fxant`. Add one line for the `${basedir}` (will make our installer icons available):

 ```xml
  <path id="fxant">
    <filelist>
      <file name="${java.home}\..\lib\ant-javafx.jar"/>
      <file name="${java.home}\lib\jfxrt.jar"/>
      <file name="${basedir}"/>
    </filelist>
  </path>
 ```

Find the following block further down in the file:

 ```xml
  <fx:resources id="appRes">
      <fx:fileset dir="dist" includes="AddressApp.jar"/>
      <fx:fileset dir="dist" includes="libs/*"/>
  </fx:resources>
 ```
 
... and insert those lines just before it:

 ```xml
  <mkdir dir="package" />

  <mkdir dir="package/windows" />
  <copy todir="package/windows">
      <fileset dir="..">
          <include name="Balloons.ico" />
          <include name="Balloons-setup-icon.bmp" />
      </fileset>
  </copy>
  
 ```



**Borrowed from** [this site]( http://edu.makery.ch/blog/2012/12/18/javafx-tutorial-addressapp-7/).
