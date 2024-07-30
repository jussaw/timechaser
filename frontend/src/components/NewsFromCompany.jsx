import React, { useState, useEffect, useRef } from "react";

export default function NewsFromCompany() {
  const [news, setNews] = useState(null);
  const scrollRef = useRef(null);

  // Set message from API
  useEffect(() => {
    setNews(
      "Raytheon's CEO has just announced an ambitious $10 billion project in collaboration with the United States Space Force. This groundbreaking initiative aims to develop a new generation of rockets designed for advanced space missions. The project underscores Raytheon's commitment to pushing the boundaries of aerospace technology and enhancing national security capabilities. With this venture, Raytheon seeks to revolutionize space exploration and defense, leveraging its extensive experience in engineering and innovation. The new rocket program is expected to deliver unprecedented performance and reliability, solidifying Raytheon's position as a leader in the aerospace industry and a key partner of the United States Space Force. This collaboration will not only advance space technology but also stimulate economic growth and create numerous high-tech jobs, contributing significantly to the aerospace sector's future.",
    );
  }, []);

  useEffect(() => {
    if (scrollRef.current) {
      const scrollElement = scrollRef.current;
      let scrollAmount = 0;
      const step = 1;
      const interval = 50;

      const startScrolling = () => {
        const scrollInterval = setInterval(() => {
          if (
            scrollAmount <
            scrollElement.scrollHeight - scrollElement.clientHeight
          ) {
            scrollAmount += step;
            scrollElement.scrollTo(0, scrollAmount);
          } else {
            clearInterval(scrollInterval);
            setTimeout(() => {
              scrollAmount = 0;
              scrollElement.scrollTo(0, 0);
              setTimeout(startScrolling, 5000);
            }, 5000);
          }
        }, interval);

        return () => clearInterval(scrollInterval);
      };

      setTimeout(startScrolling, 5000);
    }
  }, [news]);

  return (
    <div className="dashboard-component flex h-full flex-col bg-custom-white">
      <h1 className="w-full p-4 pb-0 text-center text-2xl font-semibold">
        News from Company
      </h1>
      <div
        ref={scrollRef}
        className="scrollbar-none m-6 flex h-32 overflow-y-scroll scroll-auto rounded-3xl bg-gray-200 p-4 px-6 text-start text-lg font-medium shadow-inner"
      >
        {news || "No message from manager"}
      </div>
    </div>
  );
}
