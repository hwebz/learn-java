export default function formatVNDCurrency(amount: number) {
  // Convert the amount to a string and remove any non-digit characters
  const amountStr = amount.toString().replace(/[^0-9.-]+/g, '');

  // Split the amount into integer and decimal parts
  const [integerPart, decimalPart] = amountStr.split('.');

  // Format the integer part with thousands separators
  const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

  // If there's a decimal part, format it to two decimal places, otherwise set it to an empty string
  const formattedDecimal = decimalPart ? `${decimalPart.slice(0, 2).padEnd(2, '0')}` : '';

  // Combine the formatted integer and decimal parts with a space separator
  const formattedAmount = `${formattedInteger}${formattedDecimal ? ` ${formattedDecimal}` : ''}`;

  // Prepend the currency symbol
  return `${formattedAmount} ₫`;
}