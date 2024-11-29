import React from "react";
import { Helmet } from 'react-helmet';
import './styles/App.css';
import Navbar from './componets/navbar';

const title = "WasteNot"

function App() {
  return <>
    <Helmet>
      <title>{title}</title>
    </Helmet>
    <React.Fragment>
      <Navbar/>
    </React.Fragment>
  </>;
}

export default App;
