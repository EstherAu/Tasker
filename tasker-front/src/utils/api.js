import axios from 'axios'

let base = '';

// ======== フォーム形式のリクエスト ========

/**
 * POSTリクエスト（フォーム形式）
 */
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

/**
 * ファイルアップロード（フォーム形式）
 */
export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}

/**
 * PUTリクエスト（フォーム形式）
 */
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

/**
 * GETリクエスト（フォーム形式・パラメータあり）
 */
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    url: `${base}${url}`
  });
}

/**
 * DELETEリクエスト（URLのみで実行。フォーム・JSONどちらも可）
 */
export const deleteRequest = (url) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`
  });
}

// ======== JSON形式のリクエスト（タスク管理API用） ========

/**
 * POSTリクエスト（JSON形式・タスク管理用）
 */
export const jsonPostRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'application/json'
    }
  });
};

/**
 * PUTリクエスト（JSON形式・タスク管理用）
 */
export const jsonPutRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'application/json'
    }
  });
};

/**
 * GETリクエスト（JSON形式・パラメータ付き可）
 */
export const jsonGetRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params: params,
  });
};