const BASE_URL = "http://localhost:8082/api";

function getQueryParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function saveUser(role, id) {
  localStorage.setItem("role", role);
  localStorage.setItem("userId", id);
}

function getUserId() {
  return localStorage.getItem("userId");
}
