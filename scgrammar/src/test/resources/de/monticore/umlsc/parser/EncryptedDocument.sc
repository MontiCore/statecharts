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

package de.monticore.umlsc.parser;

<<conditions="Java">>
statechart for EncryptedDocument {

  initial state WaitForUserEntry {
//    entry / {timer.set(5);} [timer.running()];
//    exit / {timer.stop();};
//    -> timeout() / {log(timer.getTimeouts() + ". timeout"); timer.set(5);}
  }

  <<encrypted>> state Identification {
    [document.isEncrypted()]
    initial final state sub1
    state sub2
    sub1 -> sub2  [check(passwd)]
    }

  state Failed

  final state Done

  <<encrypted>> WaitForUserEntry -> Identification  decrypt(document, passwd)
  WaitForUserEntry -> Failed  [timer.timeouts==3] / {log("Identification expired");}
  Identification -> Failed  [!check(passwd)]
  Identification -> Done  / {document.open();} [document.isDecrypted()]
  Failed -> Done
}
