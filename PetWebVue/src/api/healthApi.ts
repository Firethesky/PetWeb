import api from './request'

// 健康记录类型
export interface HealthRecord {
  id: number
  petId: number
  recordType: string
  recordDate: string
  weight?: number
  temperature?: number
  symptoms?: string
  diagnosis?: string
  treatment?: string
  notes?: string
}

// 健康记录API
export const healthApi = {
  // 获取宠物健康记录
  getHealthRecords(petId: number): Promise<HealthRecord[]> {
    return api.get<HealthRecord[]>(`/health-records/pet/${petId}`)
  },
  
  // 添加健康记录
  addHealthRecord(petId: number, data: Omit<HealthRecord, 'id' | 'petId'>): Promise<HealthRecord> {
    return api.post<HealthRecord>(`/health-records/pet/${petId}`, data)
  }
} 