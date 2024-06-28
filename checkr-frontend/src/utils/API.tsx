import fetch from "cross-fetch";
import { TOKEN } from "../constants";

const API_BASE_URL: string = "http://localhost:3003/";

const request = async (options: any) => {
  const headers = new Headers({
    "Content-Type": "application/json",
  });
  if (localStorage.getItem(TOKEN)) {
    headers.append("Authorization", "Bearer " + localStorage.getItem(TOKEN));
  }
  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  let response = await fetch(options.url, options);
  let json = await response.json();
  if (!response.ok) {
    return Promise.reject(json);
  }
  return json;
};

export function getAllCandidates() {
  return request({
    url: API_BASE_URL + "candidates",
    method: "GET",
  });
}

export function getCandidateById(id: any) {
  return request({
    url: API_BASE_URL + "candidates/" + id,
    method: "GET",
  });
}

export function getCandidatesReports() {
  return request({
    url: API_BASE_URL + "reports",
    method: "GET",
  });
}

export function getCourtSearches() {
  return request({
    url: API_BASE_URL + "courtSearches",
    method: "GET",
  });
}

export function getCandidateCourtSearches() {
  return request({
    url: API_BASE_URL + "candidateCourtSearches",
    method: "GET",
  });
}

export function getAllAdverseActions() {
  return request({
    url: API_BASE_URL + "adverse-action",
    method: "GET",
  });
}

export function updateReport(id: any, data: any) {
  return request({
    url: API_BASE_URL + "reports/" + id,
    method: "PATCH",
    body: JSON.stringify(data),
  });
}

export function getUsers() {
  return request({
    url: API_BASE_URL + "user",
    method: "GET",
  });
}

export function userRegistration(data: any) {
  return request({
    url: API_BASE_URL + "user",
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function getToken(data: any) {
  return fetch(API_BASE_URL + "user/token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });
}
