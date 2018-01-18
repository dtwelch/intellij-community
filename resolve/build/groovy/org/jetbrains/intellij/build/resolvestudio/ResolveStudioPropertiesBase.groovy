package org.jetbrains.intellij.build.resolvestudio

import org.jetbrains.intellij.build.ApplicationInfoProperties
import org.jetbrains.intellij.build.LinuxDistributionCustomizer
import org.jetbrains.intellij.build.MacDistributionCustomizer
import org.jetbrains.intellij.build.ProductProperties
import org.jetbrains.intellij.build.WindowsDistributionCustomizer

/**
 *
 * @author dtwelch
 */
class ResolveStudioPropertiesBase extends ProductProperties {

  //Note: Null for each of these apparently means don't build one for this particular OS... So we really only want to
  //implement the one for MacOS for now then eventually Windows (as it's objectively less important).
  @Override
  String getBaseArtifactName(ApplicationInfoProperties applicationInfo, String buildNumber) {
    return null
  }

  @Override
  WindowsDistributionCustomizer createWindowsCustomizer(String projectHome) {
    return null
  }

  @Override
  LinuxDistributionCustomizer createLinuxCustomizer(String projectHome) {
    return null
  }

  @Override
  MacDistributionCustomizer createMacCustomizer(String projectHome) {
    return null
  }
}
