/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.parser;

statechart for SingleStateClass {

  (c) state AuctionOpen {
    [timePol.status == TimingPolicy.RUNNING]
    entry / {protocol("Auction "+auctionIdent+": "+title+"started.");}
    do / {protocol("Auction running "+Time.now());}
    exit / {protocol("Auction "+auctionIdent+" finished.");}
  }
}
