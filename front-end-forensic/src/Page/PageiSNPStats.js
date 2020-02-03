import React, { Component } from 'react'
import axios from 'axios'
import { API_BASE_URL, ACCESS_TOKEN } from '../constants'

export default class PageiSNPStat extends Component {
    constructor(props) {
        super(props)

        this.state = {
            locusList: [],
            snpSummary: []
        }
    }

    componentWillMount(){
        this.props.setIsLoading(true);
        localStorage.setItem('currentMenu','isnp')
        this.props.setIsLoading(false);
    }

    componentDidMount() {
        const auth = { 'headers': { 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) } }
        axios.get(API_BASE_URL + "/resources/isnpstat", auth).then(
            function (response) {
                console.log(response.data)
                var Locus = ''
                var A_Amount = 0
                var T_Amount = 0
                var C_Amount = 0
                var G_Amount = 0
                var I_Amount = 0
                var Total_Amount = 0
                var result = []
                response.data.map(row => {
                    if (row.locus === Locus) {
                        switch (row.allele) {
                            case 'A':
                                A_Amount += row.amount
                                break
                            case 'T':
                                T_Amount += row.amount
                                break
                            case 'C':
                                C_Amount += row.amount
                                break
                            case 'G':
                                G_Amount += row.amount
                                break
                            case 'I':
                                I_Amount += row.amount
                                break
                            default:
                                break
                        }
                        Total_Amount += row.amount
                    } else {
                        result.push({
                            Locus: Locus,
                            A: A_Amount,
                            T: T_Amount,
                            C: C_Amount,
                            G: G_Amount,
                            I: I_Amount,
                            Total: Total_Amount
                        })
                        Locus = row.locus
                        A_Amount = 0
                        T_Amount = 0
                        C_Amount = 0
                        G_Amount = 0
                        Total_Amount = 0
                        switch (row.allele) {
                            case 'A':
                                A_Amount += row.amount
                                break
                            case 'T':
                                T_Amount += row.amount
                                break
                            case 'C':
                                C_Amount += row.amount
                                break
                            case 'G':
                                G_Amount += row.amount
                                break
                            case 'I':
                                I_Amount += row.amount
                                break
                            default:
                                break
                        }
                        Total_Amount += row.amount
                    }
                })
                result.push({
                    Locus: Locus,
                    A: A_Amount,
                    T: T_Amount,
                    C: C_Amount,
                    G: G_Amount,
                    I: I_Amount,
                    Total: Total_Amount
                })
                console.log(result)
                result.shift()
                this.setState({ snpSummary: result })
            }.bind(this)
        )
    }

    renderDisplay() {
        console.log(this.state.snpSummary)
        this.state.snpSummary.map(entry => {
            console.log(entry)
            return (
                <div>
                    <div className="columns">
                        <div className="column is-1">{entry.Locus}</div>
                        <div className="column">Box</div>
                        <div className="column is-1">{entry.Total}</div>
                    </div>
                </div>
            )
        })
    }

    render() {
        return (
            <div className="container">
                <br />
                <h2 className="title is-2">
                    <strong>iSNP Statistic Summary</strong>
                </h2>
                <br />
                <p>
                    <strong>
                        The statistic for iSNP data in the database are shown below, each
                        color represent the genotype found in the samples.
          </strong>
                </p>
                <br />
                <div className="columns">
                    <div
                        align="center"
                        className="column"
                        style={{ backgroundColor: '#bae637' }}
                    >
                        <strong>A</strong>
                    </div>
                    <div
                        align="center"
                        className="column"
                        style={{ backgroundColor: '#69c0ff' }}
                    >
                        <strong>T</strong>
                    </div>
                    <div
                        align="center"
                        className="column"
                        style={{ backgroundColor: '#ffd666' }}
                    >
                        <strong>C</strong>
                    </div>
                    <div
                        align="center"
                        className="column"
                        style={{ backgroundColor: '#ff7a45' }}
                    >
                        <strong>G</strong>
                    </div>
                    <div
                        align="center"
                        className="column"
                        style={{ backgroundColor: '#ffadd2' }}
                    >
                        <strong>Un-Identified ( I )</strong>
                    </div>
                </div>
                <br />
                <div>
                    <div className="columns">
                        <div className="column is-2" align="center">
                            <strong>Locus</strong>
                        </div>
                        <div className="column" align="center">
                            <strong>Percentage</strong>
                        </div>
                        <div className="column is-2" align="center">
                            <strong>Total Amount</strong>
                        </div>
                    </div>
                </div>
                {this.state.snpSummary.map(entry => {
                    // + 0.5 And Floor Function to round same as math principle
                    var a = entry.A / entry.Total * 100
                    var t = entry.T / entry.Total * 100
                    var c = entry.C / entry.Total * 100
                    var g = entry.G / entry.Total * 100
                    var i = entry.I / entry.Total * 100
                    return (
                        <div>
                            <br />

                            <div className="columns">
                                <div className="column is-2" align="center">
                                    <strong>{entry.Locus}</strong>
                                </div>
                                <div className="column">
                                    <div className="columns" style={{padding:"5px"}}>
                                        <div
                                            align="center"
                                            style={{ borderStyle: "solid", borderWidth: (a !== 0) ? "0px" : "0px", backgroundColor: '#bae637', width: (a < 7 && a > 0) ? "7%" : a + '%', height: 40 }}
                                        >
                                            {a !== 0 && <p style={{margin:"auto",padding:"10px"}}><strong>{a.toFixed(2)}%</strong></p>}
                                        </div>
                                        <div
                                            align="center"
                                            style={{ borderStyle: "solid", borderWidth: (t !== 0) ? "0px" : "0px", backgroundColor: '#69c0ff', width: (t < 7 && t > 0) ? "7%" : t + '%', height: 40 }}
                                        >
                                            {t !== 0 && <p style={{margin:"auto",padding:"10px"}}><strong>{t.toFixed(2)}%</strong></p>}
                                        </div>
                                        <div
                                            align="center"
                                            style={{ borderStyle: "solid", borderWidth: (c !== 0) ? "0px" : "0px", backgroundColor: '#ffd666', width: (c < 7 && c > 0) ? "7%" : c + '%', height: 40 }}
                                        >
                                            {c !== 0 && <p style={{margin:"auto",padding:"10px"}}><strong>{c.toFixed(2)}%</strong></p>}
                                        </div>
                                        <div
                                            align="center"
                                            style={{ borderStyle: "solid", borderWidth: (g !== 0) ? "0px" : "0px", backgroundColor: '#ff7a45', width: (g < 7 && g > 0) ? "7%" : g + '%', height: 40 }}
                                        >
                                            {g !== 0 && <p style={{margin:"auto",padding:"10px"}}><strong>{g.toFixed(2)}%</strong></p>}
                                        </div>
                                        <div
                                            align="center"
                                            style={{ borderStyle: "solid", borderWidth: (i !== 0) ? "0px" : "0px", backgroundColor: '#ffadd2', width: (i < 7 && i > 0) ? "7%" : i + '%', height: 40 }}
                                        >
                                            {i !== 0 && <p style={{margin:"auto",padding:"10px"}}><strong>{i.toFixed(2)}%</strong></p>}
                                        </div>
                                    </div>
                                </div>

                                <div className="column is-2" align="center">
                                    <strong>{entry.Total}</strong>
                                </div>
                            </div>

                        </div>
                    )
                })}
            </div>
        )
    }
}

/*
var Locus = this.state.snpSummary.map(sample => sample.Locus)
    var allele_amount = this.state.snpSummary.map(sample => sample.A)
    var chartData = {
      labels: Locus,
      datasets: [
        {
          label: 'iSNP Frequency',
          data: allele_amount,
          borderWidth: 1,
          backgroundColor: 'blue'
        }
      ]
    }


<HorizontalBar
          data={chartData}
          options={{
            maintainAspectRatio: true
          }}
        />
  */
