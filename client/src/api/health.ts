import axios from "axios";

export async function getHealthStatus() {
  const res = await axios.get("http://localhost:8080/api/health");
  return res.data;
}
