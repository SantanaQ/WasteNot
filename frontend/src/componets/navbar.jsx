import React, {Component} from "react";
import {FaMagnifyingGlass} from "react-icons/fa6";


const Button = ({text, className, onClick}) => {
    return <button className={`standard-button ${className}`} onClick={onClick}>{text}</button>;
};

const HomeButton = () => (
    <Button
        text="Home"
        className="standard-button standard-button-blue"
        onClick={() => console.log("Navigating to Home")}
    />
);

const ConfigureButton = () => (
    <Button
        text="Configure Ingredients"
        className="standard-button standard-button-plain"
        onClick={() => console.log("Navigating to Configuration")}
    />
);

const CatalogButton = () => (
    <Button
        text="Catalog"
        className="standard-button standard-button-plain"
        onClick={() => console.log("Navigating to Catalog")}
    />
);

const RegisterButton = () => (
    <Button
        text="Sign Up"
        className="standard-button standard-button-blue"
        onClick={() => console.log("Navigating to Registration")}
    />
);

const SearchBar = () => {
    return <input className="searchbar" type="text" placeholder="Search for Recipes..."/>;
};

function SearchButton() {
    return <button
        className="search-button standard-button-blue"
        onClick={() => console.log("Search for recipes")}>
        <FaMagnifyingGlass/>
    </button>
}


const ProfileButton = () => (
    <Button
        text="Profile"
        className="standard-button standard-button-blue"
        onClick={() => console.log("Navigating to Profile")}
    />
);

const LoginButton = ({onclick}) => (
    <Button
        text="Login"
        className="standard-button standard-button-plain"
        onClick={onclick}
    />
);

const LogoutButton = ({onclick}) => (
    <Button
        text="Logout"
        className="standard-button standard-button-plain"
        onClick={onclick}
    />
);

class Navbar extends Component {
    state = {
        isLoggedIn: false
    }

    handleLogin = () => {
        this.setState({isLoggedIn: true})
    };

    handleLogout = () => {
        this.setState({isLoggedIn: false})
    };

    render() {
        const {isLoggedIn} = this.state;
        return (
            <div className="navbar-container">
                <div className="navbar">
                    <div className="button-container-left">
                        <HomeButton/>
                        <ConfigureButton/>
                        <CatalogButton/>
                    </div>
                    <div className="searchbar-container">
                        <SearchBar/>
                        <SearchButton/>
                    </div>
                    <div className="button-container-right">
                        {!isLoggedIn ? (
                            <>
                                <RegisterButton/>
                                <LoginButton onclick={this.handleLogin}/>
                            </>
                        ) : (
                            <>
                                <ProfileButton/>
                                <LogoutButton onclick={this.handleLogout}/>
                            </>
                        )}
                    </div>
                </div>
            </div>
        );
    }
}

export default Navbar