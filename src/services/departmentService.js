import api from "../api/axiosConfig";

export const getDepartments = async () => {
  const response = await api.get("/departments");
  return response.data;
};