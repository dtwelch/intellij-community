package com.jetbrains.resolve;

import com.intellij.application.options.OptionId;
import com.intellij.application.options.OptionsApplicabilityFilter;

/**
 * @author dtwelch
 */
public class ResolveOptionsApplicabilityFilter extends OptionsApplicabilityFilter {
  @Override
  public boolean isOptionApplicable(OptionId optionId) {
    return optionId == OptionId.RENAME_IN_PLACE;
  }
}
