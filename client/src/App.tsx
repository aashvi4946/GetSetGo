import { useEffect, useState } from "react";
import { getHealthStatus } from "./api/health";

function App() {
  const [status, setStatus] = useState("");

  useEffect(() => {
    getHealthStatus().then(setStatus).catch(() => setStatus("Error connecting"));
  }, []);

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <h1 className="text-3xl font-bold mb-4">Travel Booking App</h1>
      <p className="text-lg">{status}</p>
    </div>
  );
}

export default App;

