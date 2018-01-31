package org.jetbrains.intellij.build.resolvestudio

import org.jetbrains.intellij.build.BuildContext
import org.jetbrains.intellij.build.WindowsDistributionCustomizer

class ResolveStudioWindowsDistributionCustomizer extends WindowsDistributionCustomizer {

  @Override
  void copyAdditionalFiles(BuildContext context, String targetDirectory) {
    def underTeamCity = System.getProperty("teamcity.buildType.id") != null

    context.ant.copy(todir: "$targetDirectory/skeletons", failonerror: underTeamCity) {
      fileset(dir: "$context.paths.projectHome/skeletons", erroronmissingdir: underTeamCity) {
        include(name: "skeletons-win*.zip")
      }
    }
  }
}
