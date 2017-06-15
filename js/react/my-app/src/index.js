import React from "react";
import ReactDOM from "react-dom";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";

class Clock extends React.Component {
  constructor(props) {
    super(props);
    this.state = {date: new Date()};
  }

  componentDidMount() {
    let updateTime = () =>
      this.setState({
        date: new Date()
      });

    this.timerID = setInterval(updateTime, 222);
  }

  componentWillUnmount() {
    clearInterval(this.timerID);
  }

  render() {
    return (
      <div>
        <h1>Hello, world!</h1>
        <h2>It is {this.state.date.toISOString()}.</h2>
      </div>
    );
  }
}

const element = <div>
  <Clock />
  <Clock />
  <Clock />
</div>;

ReactDOM.render(element, document.getElementById('root'));
registerServiceWorker();
