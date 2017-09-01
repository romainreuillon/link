pragma solidity ^0.4.15;

contract DataWards {

  function DataWards(){}

  struct proposal {
    uint keyIndex;
    uint bounty;
    address initiator;
  }

  struct proposals {
    string[] keys;
    mapping(string => proposal) proposals;
  }

//  struct response {
//    uint keyIndex;
//    uint stake;
//    mapping(address => uint) funders;
//    address attributed;
//  }
//
//  struct proposals {
//    string[] keys;
//    mapping(string => proposal) proposals;
//  }

  proposals fundingProposals;

  function propose(string proposalAddress) payable returns (bool inserted) {
    assert(msg.value > 0);

    proposal storage p = fundingProposals.proposals[proposalAddress];

    if(p.initiator == address(0)) p.initiator = msg.sender;
    p.bounty += msg.value;

    if (p.keyIndex > 0) {
      return false;
    } else {
      p.keyIndex = ++fundingProposals.keys.length;
      fundingProposals.keys[p.keyIndex - 1] = proposalAddress;
      return true;
    }
  }

  function getProposalSize() constant returns (uint) {
    return fundingProposals.keys.length;
  }

  function getProposal(uint idx) constant returns (string) {
    return fundingProposals.keys[idx];
  }

  function getProposalInformation(string _proposal) constant returns (address, uint) {
    proposal storage p = fundingProposals.proposals[_proposal];
    return (p.initiator, p.bounty);
  }


  // function getProposals(proposals.itmap self)  public constant returns (proposal[]) {
  //     proposal[] ret;

  //     uint nbAddresses = proposals.addressSize(self);
  //     for(uint addIdx = 0; addIdx < nbAddresses; addIdx++) {
  //         address add = proposals.getAddress(self, addIdx);
  //         uint nbProposals = proposals.proposalsSize(self, add);
  //         for(uint propIdx = 0; propIdx < nbProposals; nbProposals++) {
  //             bytes memory proposalAdd = proposals.getProposal(self, add, propIdx);
  //             proposal memory p = proposals.proposal(add, proposalAdd, proposals.getBounty(self, add, proposalAdd));
  //             ret.push(p);
  //         }
  //     }

  //     return ret;
  // }

}