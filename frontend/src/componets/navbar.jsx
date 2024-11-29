import React, {Component} from "react";
import { FaMagnifyingGlass } from "react-icons/fa6";
import '../App.css';

function home_button() {
    return <button className="standard-button standard-button-blue">Home</button>
}

function configure_button() {
    return <button className="standard-button standard-button-plain">Configure Ingredients</button>
}

function catalog_button() {
    return <button className="standard-button standard-button-plain">Catalog</button>
}

function login_button() {
    return <button className="standard-button standard-button-plain">Login</button>
}

function register_button() {
    return <button className="standard-button standard-button-blue">Sign Up</button>
}

function search_bar() {
    return <input className="searchbar" type="text" placeholder="Search for Recipes..."/>
}

function search_button() {
    return <button className="search-button standard-button-blue"><FaMagnifyingGlass /></button>
}

class Navbar extends Component {
    state = { }
    render() {
        return <div className="navbar-container">
            <div className="navbar">
                <div className="button-container-left">
                    {home_button()}
                    {configure_button()}
                    {catalog_button()}
                </div>
                <div className="searchbar-container">
                    {search_bar()}
                    {search_button()}
                </div>
                <div className="button-container-right">
                    {register_button()}
                    {login_button()}
                </div>
            </div>
        </div>;
    }
}

export default Navbar