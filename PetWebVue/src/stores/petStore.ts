import { defineStore } from 'pinia'
import { petApi, PetInfo } from '../api/petApi'

// 宠物状态类型
interface PetState {
  petList: PetInfo[]
  currentPet: PetInfo | null
  loading: boolean
  error: string | null
}

// 定义宠物Store
export const usePetStore = defineStore('pet', {
  // 状态
  state: (): PetState => ({
    petList: [],
    currentPet: null,
    loading: false,
    error: null
  }),
  
  // Getters
  getters: {
    // 是否有宠物
    hasPets: (state) => state.petList.length > 0
  },
  
  // Actions
  actions: {
    // 获取当前用户的所有宠物
    async fetchPets() {
      this.loading = true
      this.error = null
      
      try {
        const pets = await petApi.getMyPets()
        this.petList = pets
        return Promise.resolve(pets)
      } catch (error: any) {
        this.error = error.message || '获取宠物列表失败'
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    // 获取宠物详情
    async fetchPetDetail(petId: number) {
      this.loading = true
      this.error = null
      
      try {
        const pet = await petApi.getPetDetail(petId)
        this.currentPet = pet
        return Promise.resolve(pet)
      } catch (error: any) {
        this.error = error.message || '获取宠物详情失败'
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    // 添加宠物
    async addPet(petData: Omit<PetInfo, 'id'>) {
      this.loading = true
      this.error = null
      
      try {
        const newPet = await petApi.addPet(petData)
        this.petList.push(newPet)
        return Promise.resolve(newPet)
      } catch (error: any) {
        this.error = error.message || '添加宠物失败'
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    // 更新宠物信息
    async updatePet(petId: number, petData: Partial<PetInfo>) {
      this.loading = true
      this.error = null
      
      try {
        const updatedPet = await petApi.updatePet(petId, petData)
        
        // 更新列表中的宠物信息
        const index = this.petList.findIndex(pet => pet.id === petId)
        if (index !== -1) {
          this.petList[index] = updatedPet
        }
        
        // 更新当前宠物信息
        if (this.currentPet && this.currentPet.id === petId) {
          this.currentPet = updatedPet
        }
        
        return Promise.resolve(updatedPet)
      } catch (error: any) {
        this.error = error.message || '更新宠物信息失败'
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    }
  }
}) 