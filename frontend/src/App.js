import React from "react";
import { Helmet } from 'react-helmet';
import './styles/app.css';
import Navbar from './componets/navbar';
import MainSection from './componets/mainSection';

const title = "WasteNot"

function App() {
  return <>
    <Helmet>
      <title>{title}</title>
    </Helmet>
    <React.Fragment>
      <Navbar/>
      <MainSection/>
    </React.Fragment>
  </>;
}

export default App;
