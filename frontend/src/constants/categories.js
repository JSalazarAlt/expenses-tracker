export const CATEGORIES = [
    { value: 'FOOD', label: 'Food', icon: '🍽️' },
    { value: 'TRANSPORTATION', label: 'Transportation', icon: '🚗' },
    { value: 'UTILITIES', label: 'Utilities', icon: '💡' },
    { value: 'ENTERTAINMENT', label: 'Entertainment', icon: '🎬' },
    { value: 'HEALTHCARE', label: 'Healthcare', icon: '🏥' },
    { value: 'EDUCATION', label: 'Education', icon: '📚' },
    { value: 'PERSONAL_CARE', label: 'Personal Care', icon: '💄' },
    { value: 'MISCELLANEOUS', label: 'Miscellaneous', icon: '📦' }
];

export const getCategoryIcon = (categoryValue) => {
    const category = CATEGORIES.find(cat => cat.value === categoryValue);
    return category?.icon || '📦';
};

export const getCategoryLabel = (categoryValue) => {
    const category = CATEGORIES.find(cat => cat.value === categoryValue);
    return category?.label || categoryValue;
};