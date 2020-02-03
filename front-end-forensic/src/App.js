import React, { Component } from 'react';
import PageSearch from './Page/PageSearch';
import { Route, withRouter } from 'react-router-dom';
import GuestNavbar from './NavBar/GuestBar';
import PageUploadFileExcel from './Page/PageUploadFileExcel';
import PageStatsByGraph from './Page/PageStatsByGraph';
import { Layout } from 'antd';
import AdminNavbar from './NavBar/AdminBar';
import Login from './user/login/Login'
import SignUp from './user/signup/Signup'
import 'bulma/css/bulma.css'
import './css/Box.css'

import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from './constants';
import { notification } from 'antd';
import PageiSNPStat from './Page/PageiSNPStats';
import { Typography } from 'antd';
import PageAdminSequence from './Page/PageSequenceAlignment';
import StaticByMapPage from './Page/PageStatsByMap';
import PageListPersons from './Page/PageListPersons';
import Body from './Page/PageKinshipAnalysis';
import LoadingIndicator from './common/LoadingIndicator'
import PageUploadPersons from './Page/PageUploadPersons';
import PageUploadCEData from './Page/PageUploadCEData';
import PageViewSingleID from './Page/PageViewSingleID';
import PageWelcome from './Page/PageWelcome';

const { Title, Text } = Typography;
const { Content, Footer } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      current: localStorage.getItem('currentMenu')
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      }).catch(() => {
        this.setState({
          isLoading: false
        });
      });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  setIsLoading = (bool) => {
    this.setState({
      isLoading: bool
    })
  }

  handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: 'Forenseq App',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'Forenseq App',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />;
    }
    console.log("auth " + this.state.isAuthenticated)
    const isAuthenticated = this.state.isAuthenticated;
    // this.loadCurrentUser();
    return (
      <Layout className="layout" style={{ height: '100%', position: 'relative' }}>
        <div className="my-app" style={{ height: '100%' }}>
          {
            this.state.isAuthenticated ?
              <AdminNavbar isLoading={this.state.isLoading} onLogout={this.handleLogout} /> :
              <GuestNavbar handleLogin={this.handleLogin} />
          }
          <Content style={{ padding: '0 0px', height: 'auto' }}>
            <div style={{ background: '#e2e2e2', padding: 24, height: '100%' }}>
              <div className="App container">
                <Route exact path="/" render={(props) => <PageWelcome {...props} />} />
                <Route exact path="/Login" render={(props) => <Login onLogin={this.handleLogin} {...props} />} />
                <Route exact path="/search" render={(props) => <PageSearch isAuthenticated={this.state.isAuthenticated} {...props} />} />
                <Route exact path="/stats/graph" component={PageStatsByGraph} />
                <Route exact path="/stats/map" component={StaticByMapPage} />
                <Route exact path="/signup" component={SignUp} />
                <Route exact path="/analysis/kinship" component={Body} />
                {isAuthenticated ? <Route exact path="/adddata" render={(props) => <PageUploadFileExcel setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/isnpstat" render={(props) => <PageiSNPStat setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/seqalign" render={(props) => <PageAdminSequence setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/listpersons" render={(props) => <PageListPersons setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/uploadpersons" render={(props) => <PageUploadPersons setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/uploadcedata" render={(props) => <PageUploadCEData setIsLoading={this.setIsLoading} {...props} />} /> : null}
                {isAuthenticated ? <Route exact path="/user/view/:yid/:id" render={(props) => <PageViewSingleID setIsLoading={this.setIsLoading} {...props} />} /> : null}
              </div>
            </div>
          </Content>
          <Footer style={{ textAlign: 'center', backgroundColor: '#FFFFFF' }}><Text style={{ color: 'black', padding: 'auto' }}>&copy; 2019 CUCPBioinfo Lab, Department of Computer Engineering, Faculty of Engineering, Chulalongkorn University All Rights Reserved</Text></Footer>
        </div>
      </Layout>
    )
  }
}

export default withRouter(App);
