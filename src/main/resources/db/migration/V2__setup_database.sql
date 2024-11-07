-- Tabela tb_patient
CREATE TABLE tb_patient (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    birthday DATE NOT NULL,
    gender VARCHAR2(50) NOT NULL,  -- Substituído o tipo 'gender'
    risk_status VARCHAR2(50) NOT NULL,  -- Substituído o tipo 'risk_status'
    consultation_frequency NUMBER DEFAULT 0,
    associated_claims CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela tb_dentist
CREATE TABLE tb_dentist (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    specialty VARCHAR2(255),
    registration_number VARCHAR2(50) UNIQUE NOT NULL,
    claims_rate NUMBER(5, 2) DEFAULT 0.00,
    risk_status VARCHAR2(50) NOT NULL,  -- Substituído o tipo 'risk_status'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela tb_consultation
CREATE TABLE tb_consultation (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    consultation_date DATE NOT NULL,  -- Renomeado 'date' para 'consultation_date'
    consultation_value NUMBER(10, 2) NOT NULL,
    risk_status VARCHAR2(50) NOT NULL,
    description VARCHAR2(255),
    patient_id RAW(16) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES tb_patient(id)
);


-- Tabela tb_claim
CREATE TABLE tb_claim (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    occurrence_date DATE NOT NULL,
    value NUMBER(10, 2) NOT NULL,
    claim_type VARCHAR2(50) NOT NULL,  -- Substituído o tipo 'claim_type'
    suggested_preventive_action CLOB,
    consultation_id RAW(16) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (consultation_id) REFERENCES tb_consultation(id)
);

-- Tabela de associação entre tb_consultation e tb_dentist (Many-to-Many)
CREATE TABLE consultation_dentist (
    consultation_id RAW(16) NOT NULL,
    dentist_id RAW(16) NOT NULL,
    PRIMARY KEY (consultation_id, dentist_id),
    FOREIGN KEY (consultation_id) REFERENCES tb_consultation(id) ON DELETE CASCADE,
    FOREIGN KEY (dentist_id) REFERENCES tb_dentist(id) ON DELETE CASCADE
);
