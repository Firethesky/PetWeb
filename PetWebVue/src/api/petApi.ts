import api from './request'

// 宠物信息类型
export interface PetInfo {
  id: number
  name: string
  type: string
  breed: string
  age: number
  gender: string
  weight: number
  description?: string
  imageUrl?: string
}

// 宠物API
export const petApi = {
  // 获取当前用户的所有宠物
  getMyPets(): Promise<PetInfo[]> {
    return api.get<PetInfo[]>('/pets')
  },
  
  // 获取宠物详情
  getPetDetail(petId: number): Promise<PetInfo> {
    return api.get<PetInfo>(`/pets/${petId}`)
  },
  
  // 添加宠物
  addPet(data: Omit<PetInfo, 'id'>): Promise<PetInfo> {
    return api.post<PetInfo>('/pets', data)
  },
  
  // 更新宠物信息
  updatePet(petId: number, data: Partial<PetInfo>): Promise<PetInfo> {
    return api.put<PetInfo>(`/pets/${petId}`, data)
  }
} 