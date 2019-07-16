package com.jetbrains.resolve;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.PlatformIcons;

import javax.swing.*;

public class ResolveIcons {
  private static Icon load(String path) {
    return IconLoader.getIcon(path, ResolveIcons.class);
  }

  public static final Icon DIRECTORY = PlatformIcons.DIRECTORY_CLOSED_ICON;

  public static final Icon RESOLVE_FILE = IconLoader.getIcon("/icons/com/jetbrains/resolve/resolveFile.png");

  public static final Icon RESOLVE = IconLoader.getIcon("/icons/com/jetbrains/resolve/tool_icon.png");

  public static final Icon CONCEPT = load("/icons/com/jetbrains/resolve/concept.png");
  public static final Icon CONCEPT_EXT = load("/icons/com/jetbrains/resolve/concept_extension.png");
  public static final Icon REALIZ = load("/icons/com/jetbrains/resolve/implementation.png");
  public static final Icon FACILITY = load("/icons/com/jetbrains/resolve/facility.png");

  public static final Icon PRECIS = load("/icons/com/jetbrains/resolve/precis.png");
  public static final Icon PRECIS_EXT = load("/icons/com/jetbrains/resolve/precis_extension.png");

  public static final Icon DEF = load("/icons/com/jetbrains/resolve/def.png");
  public static final Icon TYPE_MODEL = load("/icons/com/jetbrains/resolve/type_model.png");
  public static final Icon TYPE_REPR = load("/icons/com/jetbrains/resolve/type_repr.png");
  public static final Icon GENERIC_TYPE = load("/icons/com/jetbrains/resolve/generic_type.png");
  public static final Icon PARAMETER = load("/icons/com/jetbrains/resolve/parameter_alt.png");
  public static final Icon VARIABLE = load("/icons/com/jetbrains/resolve/variable.png");

  public static final Icon RECORD_FIELD = load("/icons/com/jetbrains/resolve/record_field.png");
  public static final Icon EXEMPLAR = load("/icons/com/jetbrains/resolve/exemplar.png");

  public static final Icon FUNCTION_DECL = load("/icons/com/jetbrains/resolve/function.png");
  public static final Icon FUNCTION_IMPL = load("/icons/com/jetbrains/resolve/function_impl.png");

  public static final Icon SYMBOLS = load("/icons/com/jetbrains/resolve/symbols.png");
  public static final Icon VCG = load("/icons/com/jetbrains/resolve/vcg.png");
  public static final Icon VC = load("/icons/com/jetbrains/resolve/vc.png");

  public static final Icon VALIDATE = load("/icons/com/jetbrains/resolve/validate.png");

}
