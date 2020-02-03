import React from 'react'
import { Col, Row } from 'antd';



function maskedAllele(sequence, seqAlign) {
  let AlphaColor = {
  }
  let AlphaColorSwitch = {}
  const colours = ['#5BF13E', '#4cd631', '#FFB23E', '#e0a041', '#388BEE', '#4c8ddb', '#ED493B', '#d6554a', '#8d28cc', '#bb5ff4']
  if (seqAlign == "No Repeated Data") {
    //Do nothing
    return (<span style={{ backgroundColor: "green" }}>{sequence}</span>);
  } else {
    let alleles = seqAlign.split(' ');
    let pattern = []
    AlphaColor = {}
    AlphaColorSwitch = {}
    // console.log(AlphaColor)
    // console.log(AlphaColorSwitch)
    alleles.forEach(allele => {
      if (/\d/.test(allele)) {
        let tmp = allele.split(')');
        let tmp1 = tmp[0].split('(');
        let tmp2 = Object.assign({ pattern: tmp1[1], number: tmp[1] })
        // console.log(tmp2)
        pattern.push(tmp2)
      } else if (allele == "") {

      } else {
        let tmp2 = Object.assign({ pattern: allele, number: 1 })
        pattern.push(tmp2)
      }
    })
    // console.log(pattern)
    let k = 0;
    let final = []
    for (var i = 0; i < pattern.length; i++) {
      for (var j = 0; j < pattern[i].number; j++) {
        if (typeof AlphaColor[pattern[i].pattern] === "undefined") {
          AlphaColor[pattern[i].pattern] = colours[k++];
          AlphaColorSwitch[pattern[i].pattern] = colours[k++];
        }
        final.push(pattern[i].pattern)
      }
    }
    if (final.length == 0) {
      return <span style={{ backgroundColor: "#5BF13E" }}>{sequence}</span>
    }
    return final.map((letter, i) => (
      <span style={{ backgroundColor: (i % 2 === 0) ? AlphaColorSwitch[letter] : AlphaColor[letter] }}>{letter}</span>
    ))
  }
}

export default props => (
  <Row>
    <Col span={2}>{props.data.sampleYear}</Col>
    <Col span={2}>{props.data.sampleId}</Col>
    <Col span={14}><div style={{ wordWrap: "break-word", textAlign: "left" }}>{maskedAllele(props.data.sequence, props.data.seqAlignment)}</div></Col>
    <Col span={2}>{props.data.readCount}</Col>
    <Col span={4}>{props.data.seqAlignment}</Col>
  </Row>
)
