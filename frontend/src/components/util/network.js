const API_BASE = '/api'
const API_OPEN = API_BASE + '/open'
const API_CLOSED = API_BASE + '/closed'
const API_VERSION_V1 = '/v1'

export const EVENTS_OPEN_ENDPOINT = API_OPEN + API_VERSION_V1 + '/events'
export const EVENTS_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/events'
export const FILES_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/files'
export const PAYMENTS_OPEN_ENDPOINT = API_OPEN + API_VERSION_V1 + '/payments'
export const NEWSFEED_ENTRIES_OPEN_ENDPOINT = API_OPEN + API_VERSION_V1 + '/newsfeed-entries'
export const NEWSFEED_ENTRIES_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/newsfeed-entries'
export const TICKETS_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/tickets'
export const TICKET_TYPES_OPEN_ENDPOINT = API_OPEN + API_VERSION_V1 + '/ticket-types'
export const TICKET_TYPES_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/ticket-types'
export const USERS_OPEN_ENDPOINT = API_OPEN + API_VERSION_V1 + '/users'
export const USERS_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/users'
export const ADMIN_CLOSED_ENDPOINT = API_CLOSED + API_VERSION_V1 + '/admin'

export const fetchData = async (url, setMethod, keycloakToken) => {
  try {
    const response = await fetch(url, keycloakToken == null ? null : {
      headers: {
        Authorization: `Bearer ${keycloakToken}`
      },
    });
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    console.log(url);
    const body = await response.json();
    console.log(body);

    setMethod(body);

    console.log(body);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

//TODO all methods should function in the same way
export const postData = async (url, form, keycloakToken) => {
  const req = keycloakToken == null ? {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ ...form }),
  } : {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${keycloakToken}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ ...form })
  };
  console.log(req)
  const response = await fetch(url, req);

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response;
};

export const putData = async (url, content, keycloakToken) => {
  const request = {
    method: 'PUT',
    headers: {
      Authorization: `Bearer ${keycloakToken}`,
      'Content-Type': 'application/json',
    },
    body: content,
  }
  console.log(request);
  const response = await fetch(url, request);

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response;
}