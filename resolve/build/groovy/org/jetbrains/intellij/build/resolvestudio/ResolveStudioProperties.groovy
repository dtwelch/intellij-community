package org.jetbrains.intellij.build.resolvestudio

import org.jetbrains.intellij.build.ApplicationInfoProperties
import org.jetbrains.intellij.build.BuildContext
import org.jetbrains.intellij.build.CommunityRepositoryModules
import org.jetbrains.intellij.build.LinuxDistributionCustomizer
import org.jetbrains.intellij.build.MacDistributionCustomizer
import org.jetbrains.intellij.build.ProductProperties
import org.jetbrains.intellij.build.WindowsDistributionCustomizer

/**
 *
 * @author dtwelch
 */
class ResolveStudioProperties extends ProductProperties {

  ResolveStudioProperties(String communityHome) {
    baseFileName = "resolvestudio"
    reassignAltClickToMultipleCarets = true
    productLayout.mainJarName = "resolvestudio.jar"

    productCode = "RS"
    platformPrefix = "ResolveStudioCore"
    applicationInfoModule = "resolve-community-ide-resources"
    brandingResourcePaths = ["$communityHome/resolve/resources"]

    productLayout.platformApiModules = CommunityRepositoryModules.PLATFORM_API_MODULES + ["dom-openapi"]
    productLayout.platformImplementationModules = CommunityRepositoryModules.PLATFORM_IMPLEMENTATION_MODULES + [
      "dom-impl", "resolve-community", "resolve-community-ide-resources",
      "resolve-community-ide", "resolve-community-configure", "resolve-open-api", "platform-main"
    ]
    productLayout.bundledPluginModules = new File("$communityHome/resolve/build/plugin-list.txt").readLines()
    productLayout.mainModules = ["main_ResolveStudio"]
  }

  @Override
  void copyAdditionalFiles(BuildContext context, String targetDirectory) {
    super.copyAdditionalFiles(context, targetDirectory)
    context.ant.copy(todir: "$targetDirectory/license") {
      fileset(file: "$context.paths.communityHome/LICENSE.txt")
      fileset(file: "$context.paths.communityHome/NOTICE.txt")
    }
  }

  @Override
  String getBaseArtifactName(ApplicationInfoProperties applicationInfo, String buildNumber) {
    "resolvestudioRS-$buildNumber"
  }

  @Override
  LinuxDistributionCustomizer createLinuxCustomizer(String projectHome) {
    return null
  }

  @Override
  WindowsDistributionCustomizer createWindowsCustomizer(String projectHome) {
    return new ResolveStudioWindowsDistributionCustomizer() {
      {
        //TODO: handle these for RS.. (I haven't yet figured out how to produce an MSI installer or anything)
        installerImagesPath = "$projectHome/python/build/resources"
        fileAssociations = [".resolve"]
      }

      @Override
      String getFullNameIncludingEdition(ApplicationInfoProperties applicationInfo) {
        "PyCharm Community Edition"
      }

      @Override
      String getBaseDownloadUrlForJre() { "https://download.jetbrains.com/python" }
    }
  }

  @Override
  MacDistributionCustomizer createMacCustomizer(String projectHome) {
    return new ResolveStudioMacDistributionCustomizer() {
      {
        icnsPath = "$projectHome/resolve/resources/ResolveStudioCore.icns"
        bundleIdentifier = "com.jetbrains.resolvestudio"
        dmgImagePath = "$projectHome/python/build/DMG_background.png"
      }

      @Override
      String getRootDirectoryName(ApplicationInfoProperties applicationInfo, String buildNumber) {
        "ResolveStudio.app"
      }
    }
  }

  @Override
  String getOutputDirectoryName(ApplicationInfoProperties applicationInfo) {
    "resolvestudio"
  }
}
