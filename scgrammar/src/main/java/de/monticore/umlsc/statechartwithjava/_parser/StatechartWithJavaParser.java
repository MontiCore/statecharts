/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.monticore.umlsc.statechartwithjava._parser;

import com.google.common.io.Files;
import de.monticore.umlsc.statechart.ErrorCodesSC;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.se_rwth.commons.Names;
import de.se_rwth.commons.logging.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by eikermann on 22.06.2017.
 */
public class StatechartWithJavaParser extends StatechartWithJavaParserTOP {


  public Optional<ASTSCArtifact> parseSCArtifact(String file) throws IOException {
    return this.parseSCArtifact(Paths.get(file));

  }

  public Optional<ASTSCArtifact> parseSCArtifact(Path file) throws IOException {

    Optional<ASTSCArtifact> ast = super.parseSCArtifact(file.toString());
    if(ast.isPresent()) {
      String simpleFilename = Files.getNameWithoutExtension(file.toString());
      Optional<String> modelName = ast.get().getStatechart().getName();

      String packageName = Names.getPackageFromPath(Names.getPathFromFilename(file.toString()));
      String packageDeclaration = Names.getQualifiedName(ast.get().getPackage());

      if(!packageName.endsWith(packageDeclaration)) {
        Log.error(String.format(ErrorCodesSC.PackageName.toString(),packageDeclaration));
      }

      if(modelName.isPresent() && !modelName.get().equals(simpleFilename)) {
        Log.error(String.format(ErrorCodesSC.FileName.toString(), modelName.get(), simpleFilename));
      }
    }
    return ast;
  }
}
